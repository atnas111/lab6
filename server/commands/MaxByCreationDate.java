package server.commands;

import common.model.StudyGroup;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

public class MaxByCreationDate {
    private Set<StudyGroup> studyGroups;

    public MaxByCreationDate(Set<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
    }


    private static Optional<StudyGroup> getMaxByCreationDate(Set<StudyGroup> studyGroups) {
        return studyGroups.stream()
                .max(Comparator.comparing(StudyGroup::getCreationDate));
    }
    public static String max(Set<StudyGroup> studyGroups) {

        Optional<StudyGroup> maxStudyGroupOptional = getMaxByCreationDate(studyGroups);
        if (maxStudyGroupOptional.isPresent()) {
            return "Study group with maximum creation date: " + maxStudyGroupOptional.get();
        } else {
            return "There are no study groups in the collection.";
        }
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
