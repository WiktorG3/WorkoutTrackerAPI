package com.example.workouttracker;

import com.example.workouttracker.exercise.Exercise;
import com.example.workouttracker.repository.ExerciseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    private final ExerciseRepository exerciseRepository;

    public DataSeeder(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(exerciseRepository.count() == 0) {
            List<Exercise> exercises = Arrays.asList(
                    new Exercise("Push-Up", "An upper body strength exercise", "Strength"),
                    new Exercise("Squat", "A lower body strength exercise", "Strength"),
                    new Exercise("Running", "A cardiovascular exercise", "Cardio"),
                    new Exercise("Plank", "An exercise for core strength", "Flexibility"),
                    new Exercise("Jumping Jacks", "A full-body warm-up exercise", "Cardio")
            );
            exerciseRepository.saveAll(exercises);
            System.out.println("Database has been seeded. " + exerciseRepository.count() + " exercises added.");
        } else {
            System.out.println("Database already has been seeded.");
        }
    }
}
