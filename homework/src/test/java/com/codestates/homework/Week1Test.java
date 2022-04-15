package com.codestates.homework;

import com.codestates.homework.week1.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class Week1Test {

    @Test
    @DisplayName("[Blenders, Old, Johnnie] 와 [Pride, Monk, Walker] 를 순서대로 하나의 스트림으로 처리되는 로직 검증")
    public void problem1() {
        Flux<String> names1 = Flux.just("Blenders", "Old", "Johnnie").delayElements(Duration.ofSeconds(1));
        Flux<String> names2 = Flux.just("Pride", "Monk", "Walker").delayElements(Duration.ofSeconds(1));
        Flux<String> names = Flux.concat(names1, names2).log();

        StepVerifier.create(names)
                .expectSubscription()
                .expectNext("Blenders", "Old", "Johnnie", "Pride", "Monk", "Walker")
                .verifyComplete();
    }

    @Test
    @DisplayName("1~100 까지의 자연수 중 짝수만 출력하는 로직 검증")
    public void problem2() {

        // 1
        Flux.range(1, 100)
                .filter(num -> num % 2 == 0)
                .toIterable()
                .forEach(o -> assertThat(o % 2).isEqualTo(0));

        // 2
        Mono<Boolean> isAllEvenNumber = Flux.range(1, 100).filter(num -> num % 2 == 0).all(o -> o % 2 == 0);
        StepVerifier.create(isAllEvenNumber).expectNext(true).verifyComplete();
    }

    @Test
    @DisplayName("hello, there 를 순차적으로 publish하여 순서대로 나오는지 검증")
    public void problem3() {
        Flux<String> helloThere = Flux.just("hello", "there").log();
        StepVerifier.create(helloThere).expectNext("hello").expectNext("there").verifyComplete();
    }

    @Test
    @DisplayName("객체가 전달될 때 JOHN, JACK 등 이름이 대문자로 변환되어 출력되는 로직 검증")
    public void problem4() {

        Person john = new Person("John", "[john@gmail.com](mailto:john@gmail.com)", "12345678");
        Person jack = new Person("Jack", "[jack@gmail.com](mailto:jack@gmail.com)", "12345678");

        // ? 중첩된 flux는 어떻게 테스트 하면 좋을까?
        var people = Flux.just(john, jack).map(person -> {
                    String name = person.getName();
                    return new String[]{name.toUpperCase(), person.getEmail(), person.getNumber()};
                }
        );

        StepVerifier.create(people)
                .assertNext(o -> {
                    assertThat(o[0]).isEqualTo("JOHN");
                })
                .assertNext(o -> {
                    assertThat(o[0]).isEqualTo("JACK");
                })
                .verifyComplete();
    }


    @Test
    @DisplayName("[Blenders, Old, Johnnie] 와 [Pride, Monk, Walker]를 압축하여 스트림으로 처리 검증")
    public void problem5() {
        Flux<String> flux1 = Flux.just("Blenders", "Old", "Johnnie");
        Flux<String> flux2 = Flux.just("Pride", "Monk", "Walker");

        Flux<String> fluxZip = Flux.zip(flux1, flux2)
                .flatMap(aaa -> Mono.just(String.format("%s %s", aaa.getT1(), aaa.getT2())));

        StepVerifier.create(fluxZip).expectNext("Blenders Pride", "Old Monk", "Johnnie Walker").verifyComplete();

    }

    @Test
    @DisplayName("[google, abc, fb, stackoverflow] 의 문자열 중 5자 이상 되는 문자열만 대문자로 비동기로 치환하여 1번 반복하는 스트림으로 처리하는 로직 검증")
    public void problem6() {
        Flux<String> texts = Flux.just("google", "abc", "fb", "stackoverflow")
                .log()
                .subscribeOn(Schedulers.parallel())
                .filter(o -> o.length() >= 5).repeat(1);

        StepVerifier.create(texts)
                .expectNext("google").expectNext("stackoverflow")
                .expectNext("google").expectNext("stackoverflow")
                .verifyComplete();

    }
}

