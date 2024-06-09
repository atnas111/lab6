package server.commands;

import common.model.StudyGroup;

import java.util.Set;

public class CountBy {
    private static Set<StudyGroup> studyGroups;

    public CountBy(Set<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
    }

    public void execute(String[] args) {
        if (args.length != 2) {
            System.out.println("Invalid command syntax. Usage: count_by_group_admin groupAdmin");
            return;
        }

        String groupAdminName = args[1];
        long count = countByGroupAdmin(groupAdminName);
        System.out.println("Number of study groups with group admin \"" + groupAdminName + "\": " + count);
    }

    private static long countByGroupAdmin(String groupAdminName) {
        return studyGroups.stream()
                .filter(group -> group.getGroupAdmin().getName().equalsIgnoreCase(groupAdminName))
                .count();
    }
    public static String countby(String[] args) {
        if (args.length != 2) {
            System.out.println("Invalid command syntax. Usage: count_by_group_admin groupAdmin");
            return null;
        }

        String groupAdminName = args[1];
        long count = countByGroupAdmin(groupAdminName);
        return "Number of study groups with group admin \"" + groupAdminName + "\": " + count;
    }
}
