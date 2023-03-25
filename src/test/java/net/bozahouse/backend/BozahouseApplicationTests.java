package net.bozahouse.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


class BozahouseApplicationTests {

	Calculator calco = new Calculator();

	@Test
	void itShouldAddNumber() {
		//given
		int numberOne = 20;
		int numberTwo = 30;

		//when
		int result = calco.add(numberOne, numberTwo);

		//then
		int expected = 50;
		assertThat(result).isEqualTo(expected);
		assertEquals(50, result);

	}

	class Calculator {
		int add(int a, int b){
			return a + b;
		}
	}

}
