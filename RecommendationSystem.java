package healthmonitoring;

import java.util.ArrayList;
import java.util.List;

public class RecommendationSystem {
        private static final int MIN_HEART_RATE = 60;
        private static final int MAX_HEART_RATE = 100;
        private static final int MIN_STEPS = 10000;
        private static final int RECOMMENDED_SLEEP_DURATION = 7;

        public List<String> generateRecommendations(HealthData healthData) {
                List<String> recommendations = new ArrayList<>();

                int heartRate = healthData.getHeartRate();
                if (heartRate < MIN_HEART_RATE) {
                        recommendations.add("Your heart rate is lower than the recommended range. " +
                                        "Consider increasing your physical activity to improve your cardiovascular health.");
                } else if (heartRate > MAX_HEART_RATE) {
                        recommendations.add("Your heart rate is higher than the recommended range. " +
                                        "Consider relaxing activities and consult your doctor if this condition persists.");
                }

                int steps = healthData.getSteps();
                if (steps < MIN_STEPS) {
                        recommendations.add("You're not reaching the recommended daily step count. " +
                                        "Try to incorporate more walking or other physical activities into your daily routine.");
                }

                int sleepDuration = healthData.getSleepDuration();
                if (sleepDuration < RECOMMENDED_SLEEP_DURATION) {
                        recommendations.add("Your sleep duration is less than the recommended 7-9 hours for adults. " +
                                        "Consider adjusting your bedtime routine for better sleep quality.");
                }

                return recommendations;
        }
}
