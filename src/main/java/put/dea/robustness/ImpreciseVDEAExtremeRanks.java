package put.dea.robustness;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

/**
 * Calculates the extreme (minimal and maximal) ranks
 * of analysed DMUs in problems with imprecise information and VDEA model
 */
public class ImpreciseVDEAExtremeRanks extends VDEABase
        implements ExtremeRanks<ImpreciseVDEAProblemData> {

    private final ImprecisePerformanceConverter performanceConverter = new ImprecisePerformanceConverter();
    private final ImpreciseVDEAUtils impreciseCommonUtils;
    private final ExtremeRanksBase extremeRanksBase = new ExtremeRanksBase();

    /**
     * Creates a new object with default minimal ratios for ordinal factors (1.0001)
     * and marginal functions (0.0001)
     * and minimal ordinal performance (1.0)
     */
    public ImpreciseVDEAExtremeRanks() {
        this(1.00001, 1e-4, 1.0);
    }


    /**
     * Creates a new object with given minimal ratios for ordinal factors and marginal function values
     * and minimal ordinal performance
     *
     * @param alpha               minimal ratio between subsequent performances in ordinal factors
     * @param epsilon             minimal performance for ordinal factors
     * @param functionValuesAlpha minimal ratio between subsequent performances in marginal value function
     */
    public ImpreciseVDEAExtremeRanks(double alpha, double epsilon, double functionValuesAlpha) {
        impreciseCommonUtils = new ImpreciseVDEAUtils(alpha, epsilon, functionValuesAlpha);
    }


    @Override
    public int minRank(ImpreciseVDEAProblemData data, int subjectDmuIdx) {
        return createModel(data, subjectDmuIdx, OptimizationSense.MINIMIZE,
                NEGATIVE_INFINITY, 0.0);
    }

    @Override
    public int maxRank(ImpreciseVDEAProblemData data, int subjectDmuIdx) {
        return createModel(data, subjectDmuIdx, OptimizationSense.MAXIMIZE,
                -C, POSITIVE_INFINITY);
    }

    private int createModel(ImpreciseVDEAProblemData data, int subjectDmuIdx,
                            OptimizationSense sense, Double constraintsLower, Double constraintUpper) {

        var preciseData = performanceConverter.convertPerformanceToPrecise(data.getImpreciseInformation(),
                subjectDmuIdx,
                sense.isMaximize() ? ResultType.PESSIMISTIC : ResultType.OPTIMISTIC);

        var model = makeModel(sense);

        var inputWeights = makeWeightVariables(model, preciseData.getInputData());
        var outputWeights = makeWeightVariables(model, preciseData.getOutputData());

        var binVariables = extremeRanksBase.
                createBinaryVariablesAndObjective(model, data.getDmuCount(), subjectDmuIdx);

        addSumWeightsToOneConstraint(model, inputWeights, outputWeights);

        var performanceVariables = impreciseCommonUtils.makeOrdinalAndFunctionRangeVariables(model, data);
        impreciseCommonUtils.addMonotonicityConstraints(model, data.getImpreciseInformation());
        impreciseCommonUtils.addFunctionRangeConstraints(model, data, preciseData, performanceVariables);

        for (int k = 0; k < data.getDmuCount(); k++) {
            if (k != subjectDmuIdx) {
                var constraint = impreciseCommonUtils.createImpreciseDistanceConstraint(
                        model, data, subjectDmuIdx, k);
                constraint.setBounds(constraintsLower, constraintUpper);
                constraint.setCoefficient(binVariables[k], -C);
            }
        }

        addCustomWeightConstraints(data, model);
        return (int) Math.round(getModelResult(model));
    }
}
