package put.dea.robustness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

public class ImpreciseCCRTransformationTests extends ImpreciseCCRTestBase {
    @Test
    public void verifyOptimisticTransformation() {
        var converter = new ImprecisePerformanceConverter();
        var actual = converter.convertPerformanceToPrecise(data.getImpreciseInformation(),
                0, ResultType.OPTIMISTIC);
        Assertions.assertArrayEquals(TestUtils.tranformTableToArray(data.getInputData()),
                TestUtils.tranformTableToArray(actual.getInputData()));
        Assertions.assertEquals(data.getOutputCount(), actual.getOutputCount());
        Assertions.assertArrayEquals(data.getOutputData().doubleColumn(1).asDoubleArray(),
                actual.getOutputData().doubleColumn(1).asDoubleArray());
        var expectedCapacity =
                Stream.of(65, 60, 40, 1, 45, 1, 4, 10, 9, 5, 25, 10, 8, 20, 40, 75,
                                10, 9, 10, 1, 25, 0.8, 2, 1, 8, 65, 190)
                        .mapToDouble(Number::doubleValue)
                        .boxed()
                        .toList();
        Assertions.assertIterableEquals(expectedCapacity, actual.getOutputData().doubleColumn(0));
    }

    @Test
    public void verifyPessimisticTransformation() {
        var converter = new ImprecisePerformanceConverter();
        var actual = converter.convertPerformanceToPrecise(
                data.getImpreciseInformation(), 0, ResultType.PESSIMISTIC);
        Assertions.assertArrayEquals(TestUtils.tranformTableToArray(data.getInputData()),
                TestUtils.tranformTableToArray(actual.getInputData()));
        Assertions.assertEquals(data.getOutputCount(), actual.getOutputCount());
        Assertions.assertIterableEquals(data.getOutputData().doubleColumn(1),
                actual.getOutputData().doubleColumn(1));
        var expectedCapacity =
                Stream.of(50, 70, 50, 3, 55, 2, 5, 20, 12, 8, 35, 15, 12, 35, 55,
                                85, 18, 15, 13, 4, 30, 1.2, 4, 5, 12, 80, 220)
                        .mapToDouble(Number::doubleValue)
                        .boxed()
                        .toList();
        Assertions.assertIterableEquals(expectedCapacity, actual.getOutputData().doubleColumn(0));
    }
}
