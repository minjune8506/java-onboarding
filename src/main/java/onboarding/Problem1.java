package onboarding;

import java.util.List;

class Problem1 {
    private static final int LEFT = 0;
    private static final int RIGHT= 1;

    public static boolean isValidRange(int number) {
        if (number < 1 || number > 400) {
            return false;
        }
        return true;
    }
    public static boolean isOdd(int number) {
        return number % 2 == 1;
    }

    public static boolean isEven(int number) {
        return number % 2 == 0;
    }

    public static boolean isValidPageNumber(List<Integer> pages) {
        int left = pages.get(LEFT);
        int right = pages.get(RIGHT);

        // page의 범위가 벗어난 경우
        if (!isValidRange(left) || !isValidRange(right)) {
            return false;
        }
        // 왼쪽이 짝수거나 오른쪽이 홀수인 경우
        if (!isOdd(left) || !isEven(right)) {
            return false;
        }
        // 연속적인 페이지가 아닌 경우
        if (left + 1 != right) {
            return false;
        }
        // 시작면이나 마지막면이 나온 경우
        if (left == 1 || left == 399) {
            return false;
        }
        return true;
    }

    public static int getBiggestValue(int page) {
        int sum = 0;
        int multiply = 1;

        while (page != 0) {
            int number = page % 10;
            sum += number;
            multiply *= number;
            page /= 10;
        }
        return Math.max(sum, multiply);
    }

    public static int getScore(List<Integer> pages) {
        int score = -1;

        for (int i = 0; i < pages.size(); i++) {
            int biggestValue = getBiggestValue(pages.get(i));
            if (score < biggestValue) {
                score = biggestValue;
            }
        }
        return score;
    }

    public static int getWinner(List<Integer> pobi, List<Integer> crong) {
        int pobiScore = getScore(pobi);
        int crongScore = getScore(crong);

        if (pobiScore > crongScore) {
            return 1; // pobi win
        } else if (pobiScore < crongScore) {
            return 2; // crong win
        } else {
            return 0; // tie
        }
    }

    public static int solution(List<Integer> pobi, List<Integer> crong) {
        int answer = 0;

        // 예외 처리
        if (!isValidPageNumber(pobi) || !isValidPageNumber(crong)) {
            return -1;
        }
        // 승자 구하기(solve)
        answer = getWinner(pobi, crong);
        return answer;
    }
}
