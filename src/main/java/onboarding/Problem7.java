package onboarding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Problem7 {
    private static final int FROM = 0;
    private static final int TO = 1;

    private static Map<String, Set<String>> getRelation(List<List<String>> friends) {
        Map<String, Set<String>> friendsByName = new HashMap<>();

        for (List<String> friend : friends) {
            String from = friend.get(FROM);
            String to = friend.get(TO);
            if (!friendsByName.containsKey(from)) {
                friendsByName.put(from, new HashSet<>());
            }
            if (!friendsByName.containsKey(to)) {
                friendsByName.put(to, new HashSet<>());
            }
            friendsByName.get(from).add(to);
            friendsByName.get(to).add(from);
        }
        return friendsByName;
    }
    private static Map<String, Integer> getVisitorScore(List<String> visitors) {
        Map<String, Integer> visitorScore = new HashMap<>();

        for (String visitor : visitors) {
            if (!visitorScore.containsKey(visitor)) {
                visitorScore.put(visitor, 0);
            }
            int visitCnt = visitorScore.get(visitor);
            visitorScore.replace(visitor, visitCnt + 1);
        }
        return visitorScore;
    }

    private static void storeCandidatesFromFriendsByName(Set<String> candidates,
                                                         Map<String, Set<String>> friendsByName,
                                                         List<String> userFriends,
                                                         String user) {
        for (String name : friendsByName.keySet()) {
            if (!userFriends.contains(name) && !name.equals(user)) {
                candidates.add(name);
            }
        }
    }

    private static void storeCandidatesFromVisitorScore(Set<String> candidates,
                                                        Map<String, Integer> visitorScore,
                                                        List<String> userFriends,
                                                        String user) {
        for (String visitor : visitorScore.keySet()) {
            if (!userFriends.contains(visitor) && !visitor.equals(user)) {
                candidates.add(visitor);
            }
        }
    }

    private static Set<String> getCandidates(Map<String, Set<String>> friendsByName,
                                             Map<String, Integer> visitorScore,
                                             List<String> userFriends,
                                             String user) {
        Set<String> candidates = new HashSet<>();

        storeCandidatesFromFriendsByName(candidates, friendsByName, userFriends, user);
        storeCandidatesFromVisitorScore(candidates, visitorScore, userFriends, user);
        return candidates;
    }

    private static Map<String, Integer> createScoreByName(Set<String> candidates) {
        Map<String, Integer> scoreByName = new HashMap<>();

        for (String candidate : candidates) {
            scoreByName.put(candidate, 0);
        }
        return scoreByName;
    }

    private static void calcFriendScore(Map<String, Integer> scoreByName,
                                        Map<String, Set<String>> friendByName,
                                        List<String> userFriends) {
        Set<String> names = scoreByName.keySet();

        for (String name : names) {
            Set<String> friends = friendByName.get(name);
            if (friends == null) {
                continue;
            }
            for (String userFriend : userFriends) {
                if (friends.contains(userFriend)) {
                    Integer score = scoreByName.get(name);
                    scoreByName.replace(name, score + 10);
                }
            }
        }
    }

    private static void calcVisitorScore(Map<String, Integer> scoreByName,
                                         Map<String, Integer> visitorScore) {
        Set<String> names = scoreByName.keySet();

        for (String name : names) {
            if (visitorScore.containsKey(name)) {
                scoreByName.replace(name, visitorScore.get(name));
            }
        }
    }

    private static void sortRecommendList(List<Map.Entry<String, Integer>> recommendList) {
        recommendList.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if (o1.getValue().equals(o2.getValue())) {
                    return o1.getKey().compareTo(o2.getKey());
                }
                return o2.getValue().compareTo(o1.getValue());
            }
        });
    }

    public static List<String> checkRecommendList(List<Map.Entry<String, Integer>> recommendList) {
        List<String> checkedList = new ArrayList<>();

        for (Map.Entry<String, Integer> score : recommendList) {
            if (score.getValue() != 0) {
                checkedList.add(score.getKey());
            }
        }
        if (checkedList.size() > 5) {
            checkedList = checkedList.subList(0, 5);
        }
        return checkedList;
    }

    public static List<String> solution(String user, List<List<String>> friends, List<String> visitors) {
        List<String> answer = Collections.emptyList();
        Map<String, Set<String>> friendsByName;
        List<String> userFriends;
        Map<String, Integer> visitorScore;
        Set<String> candidates;
        Map<String, Integer> scoreByName;
        List<Map.Entry<String, Integer>> recommendList;

        friendsByName = getRelation(friends);
        userFriends = new ArrayList<>(friendsByName.get(user));
        visitorScore = getVisitorScore(visitors);
        candidates = getCandidates(friendsByName, visitorScore, userFriends, user);
        scoreByName = createScoreByName(candidates);
        calcFriendScore(scoreByName, friendsByName, userFriends);
        calcVisitorScore(scoreByName, visitorScore);
        recommendList = new ArrayList<>(scoreByName.entrySet());
        sortRecommendList(recommendList);
        answer = checkRecommendList(recommendList);
        return answer;
    }
}
