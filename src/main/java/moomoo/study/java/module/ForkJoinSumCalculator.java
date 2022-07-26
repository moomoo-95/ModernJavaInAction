package moomoo.study.java.module;

import java.util.concurrent.RecursiveTask;

/**
 * RecursiveTask 를 상속받아 포크/조인 프레임워크에서 사용할 태스크를 생성
 */
public class ForkJoinSumCalculator  extends RecursiveTask<Long> {

    // 더 이상 분할 불가능한 서브 태스크 수
    public static final long THRESHOLD = 10_000;

    private final long[] numbers;
    private final int start;
    private final int end;

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            // 최소 분할 기준값 이하일 경우 계산
            return computeSequentially();
        }
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, (start + length)/2);
        // 다른 스레드로 비동기 실행
        leftTask.fork();
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, (start + length)/2, end);
        // 동기 실행, 내부에서 추가 비동기 실행 일어날 수 있음
        Long rightResult = rightTask.compute();
        // 첫 번째 서브태스크 결과를 읽음, 결과가 없으면 기다림
        Long leftResult = leftTask.join();
        return leftResult + rightResult;
    }

    private long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }
}
