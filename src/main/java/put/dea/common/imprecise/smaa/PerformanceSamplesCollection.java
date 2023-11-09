package put.dea.common.imprecise.smaa;

import joinery.DataFrame;

import java.util.ArrayList;
import java.util.List;

public class PerformanceSamplesCollection {
    private final List<DataFrame<Double>> inputPerformances;
    private final List<DataFrame<Double>> outputPerformances;

    public PerformanceSamplesCollection() {
        this.inputPerformances = new ArrayList<>();
        this.outputPerformances = new ArrayList<>();
    }

    public List<DataFrame<Double>> getInputPerformances() {
        return inputPerformances;
    }

    public List<DataFrame<Double>> getOutputPerformances() {
        return outputPerformances;
    }

}
