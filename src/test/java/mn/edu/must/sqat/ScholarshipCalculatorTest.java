package mn.edu.must.sqat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ScholarshipCalculatorTest {

    private final ScholarshipCalculator calc = new ScholarshipCalculator();

    // ===================== A: Happy path (commit 1) =====================

    @Test
    void letterGrade_95_isA() {
        assertEquals("A", calc.letterGrade(95));
    }

    @Test
    void gpa_95_85_is3point5() {
        assertEquals(3.5, calc.gpa(new double[]{95, 85}), 0.0001);
    }

    @Test
    void scholarship_high_gpa_full_credits_is100() {
        assertEquals(100, calc.scholarship(3.9, 16, false));
    }

    @Test
    void scholarship_3point5_13credits_is70() {
        assertEquals(70, calc.scholarship(3.5, 13, false));
    }

    @Test
    void scholarship_3point1_is50() {
        assertEquals(50, calc.scholarship(3.1, 10, false));
    }

}
