package put.dea.robustness;

import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Map;

public class HierarchicalVDEATestBase {
    protected HierarchicalVDEAProblemData data;

    @BeforeEach
    public void initializeData() {
        var inputs = new double[][]{
                new double[]{4.05, 46.1, 19.23},
                new double[]{5.36, 38.9, 27.04},
                new double[]{7.11, 44.8, 22.19},
                new double[]{6.93, 49.4, 19.47},
                new double[]{5.83, 42, 18.32},
                new double[]{4.36, 43.4, 15.16},
                new double[]{6.8, 46.5, 22.36},
                new double[]{5.32, 47.4, 20.26},
                new double[]{6.74, 51.8, 13.4},
                new double[]{7.66, 50.8, 24.55},
                new double[]{6.88, 44.1, 23.03},
                new double[]{5.88, 50.6, 19.9},
                new double[]{4.54, 55.5, 22.02},
                new double[]{5.79, 49.5, 12.44},
                new double[]{5.61, 43.7, 18.27},
                new double[]{5.76, 48, 14.39}

        };

        var outputs = new double[][]{
                new double[]{44.44, 17.6, -1.5, 46.9, 3.55, 9.3},
                new double[]{45.31, 21.3, 0.08, 51.3, 3.82, 33.33},
                new double[]{43.34, 15.7, -1.09, 47.8, 3.72, 22.73},
                new double[]{37.54, 15.8, 2.3, 43.4, 3.61, 11.11},
                new double[]{50.21, 18, 1.9, 51.3, 3.84, 13.04},
                new double[]{47.88, 25.7, -0.3, 50.5, 3.69, 17.24},
                new double[]{39.9, 15.7, 3.8, 41.8, 3.77, 33.33},
                new double[]{38.59, 16.7, -1.57, 47.8, 3.62, 20.56},
                new double[]{45.17, 8.1, 0.41, 43, 3.77, 20.37},
                new double[]{45.04, 13.8, -1.77, 43.8, 3.57, 17.28},
                new double[]{33.71, 10, -0.36, 40.9, 3.67, 35.71},
                new double[]{42, 6, 1.02, 47.3, 3.78, 15.15},
                new double[]{40.7, 13.9, -1.63, 39.1, 3.75, 16.88},
                new double[]{41.14, 7.8, 0.17, 47.2, 3.57, 29.17},
                new double[]{32.22, 9.1, 0.79, 44.3, 3.64, 25.84},
                new double[]{38.73, 1.9, -4.31, 45.8, 3.84, 20.51}
        };
        var hierarchy = new HierarchyNode("index");
        var health = new HierarchyNode("health_improvement");
        hierarchy.addChild(health);
        health.addChild(new HierarchyNode("h1"));
        health.addChild(new HierarchyNode("h2"));
        health.addChild(new HierarchyNode("h3"));

        var finances = new HierarchyNode("finances");
        hierarchy.addChild(finances);
        finances.addChild(new HierarchyNode("f1"));
        finances.addChild(new HierarchyNode("f2"));
        finances.addChild(new HierarchyNode("f3"));

        var satisfaction = new HierarchyNode("satisfaction");
        hierarchy.addChild(satisfaction);
        satisfaction.addChild(new HierarchyNode("s1"));
        satisfaction.addChild(new HierarchyNode("s2"));
        satisfaction.addChild(new HierarchyNode("s3"));

        data = new HierarchicalVDEAProblemData(inputs, outputs,
                List.of("h2", "f2", "s1"), List.of("h1", "h3", "f1", "f3", "s2", "s3"),
                hierarchy);

        addFunctionShapes();
        addWeightConstraints();

    }

    private void addFunctionShapes() {
        data.setFunctionShape("h1", List.of(
                new Pair<>(32.0, 0.0),
                new Pair<>(38.0, 0.3),
                new Pair<>(46.0, 0.9),
                new Pair<>(51.0, 1.0)
        ));

        data.setFunctionShape("h2", List.of(
                new Pair<>(4.0, 1.0),
                new Pair<>(4.6, 0.9),
                new Pair<>(6.0, 0.5),
                new Pair<>(7.15, 0.1),
                new Pair<>(7.7, 0.0)
        ));

        data.setFunctionShape("h3", List.of(
                new Pair<>(1.8, 0.0),
                new Pair<>(6.0, 0.1),
                new Pair<>(18.0, 0.8),
                new Pair<>(25.8, 1.0)
        ));

        data.setFunctionShape("f1", List.of(
                new Pair<>(-4.5, 0.0),
                new Pair<>(-2.0, 0.2),
                new Pair<>(2.0, 0.8),
                new Pair<>(4.0, 1.0)
        ));

        data.setFunctionShape("f2", List.of(
                new Pair<>(38.0, 1.0),
                new Pair<>(47.0, 0.3),
                new Pair<>(56.0, 0.0)
        ));

        data.setFunctionShape("f3", List.of(
                new Pair<>(39.0, 0.0),
                new Pair<>(45.0, 0.7),
                new Pair<>(51.5, 1.0)
        ));

        data.setFunctionShape("s1", List.of(
                new Pair<>(12.4, 1.0),
                new Pair<>(15.0, 0.6),
                new Pair<>(20.0, 0.4),
                new Pair<>(27.1, 0.0)
        ));

        data.setFunctionShape("s2", List.of(
                new Pair<>(1.0, 0.0),
                new Pair<>(3.55, 0.05),
                new Pair<>(3.84, 0.95),
                new Pair<>(5.0, 1.0)
        ));

        data.setFunctionShape("s3", List.of(
                new Pair<>(9.0, 0.0),
                new Pair<>(16.0, 0.3),
                new Pair<>(21.0, 0.6),
                new Pair<>(36.0, 1.0)
        ));
    }

    private void addWeightConstraints() {
        data.addWeightConstraint(new Constraint(
                ConstraintOperator.GEQ,
                0,
                Map.of("health_improvement", 1.0, "finances", -1.0)
        ));

        data.addWeightConstraint(new Constraint(
                ConstraintOperator.GEQ,
                0,
                Map.of("health_improvement", 1.0, "satisfaction", -1.0)
        ));

        data.addWeightConstraint(new Constraint(
                ConstraintOperator.GEQ,
                0.2,
                Map.of("finances", 1.0)
        ));

        data.addWeightConstraint(new Constraint(
                ConstraintOperator.GEQ,
                0.2,
                Map.of("satisfaction", 1.0)
        ));

        var categoryPrefixes = List.of("h", "f", "s");
        for (var prefix : categoryPrefixes) {
            for (int i = 1; i <= 3; i++) {
                data.addWeightConstraint(new Constraint(
                        ConstraintOperator.GEQ,
                        0.2,
                        Map.of(prefix + i, 1.0)
                ));
                data.addWeightConstraint(new Constraint(
                        ConstraintOperator.LEQ,
                        0.5,
                        Map.of(prefix + i, 1.0)
                ));
            }
        }
    }
}
