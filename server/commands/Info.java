package server.commands;

import common.model.StudyGroup;

import java.util.Set;

public class Info {
    private static Set<StudyGroup> studyGroups;

    public Info(Set<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;}



    public void execute(String[] args) {
        System.out.println("Collection type: LinkedHashSet<StudyGroup>");
        System.out.println("Number of elements in the collection: " + studyGroups.size());
    }
    public static String info(Set<StudyGroup> studyGroups){
        return "Collection type: LinkedHashSet<StudyGroup>\n"+
                "Number of elements in the collection: " + studyGroups.size();
    }
}
