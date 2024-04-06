import java.util.ArrayList;
import java.util.List;

public class RecommendationSystem {
        private static final int MIN_HEART_RATE = 60;
        private static final int MAX_HEART_RATE = 100;
        private static final int MIN_STEPS = 10000;
        private static final double MIN_SLEEP_HOURS = 7.0;
        private static final double MAX_SLEEP_HOURS = 8.0;

        public List<String> generateRecommendations(HealthData healthData) {
                List<String> recommendations = new ArrayList<>();

                // Analyze heart rate
                int heartRate = healthData.getHeartRate();
                if (heartRate < MIN_HEART_RATE) {
                        recommendations.add("Your heart rate is lower than the recommended range. " +
                                        "Consider increasing your physical activity to improve your cardiovascular health.");
                } else if (heartRate > MAX_HEART_RATE) {
                        recommendations.add("Your heart rate is higher than the recommended range. " +
                                        "Consider consulting a healthcare professional for further evaluation.");
                }

                // Analyze steps
                int steps = healthData.getSteps();
                if (steps < MIN_STEPS) {
                        recommendations.add("You're not reaching the recommended daily step count of 10,000 steps. " +
                                        "Try to incorporate more walking or other physical activities into your daily routine.");
                } else {
                        recommendations.add("Great job on reaching the recommended daily step count! " +
                                        "Keep up the good work to maintain your physical health.");
                }

                // Analyze sleep duration in hours
                double sleepHours = healthData.getSleepDuration() / 60.0; // Convert minutes to hours
                if (sleepHours < MIN_SLEEP_HOURS) {
                        recommendations.add("Your sleep duration is less than the recommended 7 hours. " +
                                        "Consider adjusting your schedule to ensure you get enough rest.");
                } else if (sleepHours > MAX_SLEEP_HOURS) {
                        recommendations.add("Your sleep duration is more than the recommended 8 hours. " +
                                        "Excessive sleep can be a sign of other health issues. Consider consulting a healthcare professional if you consistently sleep more than 8 hours.");
                }

                return recommendations;
        }
}