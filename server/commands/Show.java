package server.commands;
import common.model.StudyGroup;

import java.io.FileNotFoundException;
import java.util.Set;

public class Show {
    private static Set<StudyGroup> studyGroups;

    public Show(Set<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
    }

    public static String execute(Set<StudyGroup> studyGroups) {
        StringBuilder result = new StringBuilder();
        if (studyGroups.isEmpty()) {
            result.append("The collection is empty.");
        } else {
            result.append("Elements in the collection:\n");
            for (StudyGroup group : studyGroups) {
                try {
                    result.append(formatStudyGroup(group)).append("\n");
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result.toString();
    }

    private static String formatStudyGroup(StudyGroup group) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(group.getId()).append("\n");
        sb.append("Name: ").append(group.getName()).append("\n");
        sb.append("Creation Date: ").append(group.getCreationDate()).append("\n");
        sb.append("Students Count: ").append(group.getStudentsCount()).append("\n");
        sb.append("Average Mark: ").append(group.getAverageMark()).append("\n");
        sb.append("Form of Education: ").append(group.getFormOfEducation()).append("\n");
        sb.append("Semester: ").append(group.getSemesterEnum()).append("\n");
        sb.append("Group Admin: ").append(group.getGroupAdmname()).append("\n");
        return sb.toString();
    }
}
