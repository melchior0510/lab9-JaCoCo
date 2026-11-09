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

    // ===================== B: Branch 100% (commit 2) =====================

    // letterGrade: үсэг бүр + хүрээнээс гарсан оролт
    @Test
    void letterGrade_85_isB() { assertEquals("B", calc.letterGrade(85)); }

    @Test
    void letterGrade_75_isC() { assertEquals("C", calc.letterGrade(75)); }

    @Test
    void letterGrade_65_isD() { assertEquals("D", calc.letterGrade(65)); }

    @Test
    void letterGrade_30_isF() { assertEquals("F", calc.letterGrade(30)); }

    @Test
    void letterGrade_negative_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(-1));
    }

    @Test
    void letterGrade_over100_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(101));
    }

    // gpa: switch-ийн бүх case + default (F)
    @Test
    void gpa_allLetters_including_F_is2point0() {
        assertEquals(2.0, calc.gpa(new double[]{95, 85, 75, 65, 30}), 0.0001);
    }

    // scholarship: || нөхцөл бүрийн үнэн утга (3 тусдаа assertThrows)
    @Test
    void scholarship_gpaNegative_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(-0.1, 15, false));
    }

    @Test
    void scholarship_gpaAbove4_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(4.1, 15, false));
    }

    @Test
    void scholarship_creditsNegative_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(3.5, -1, false));
    }

    // scholarship: && нөхцөл бүрийн F утга
    @Test
    void scholarship_violation_is0() {
        assertEquals(0, calc.scholarship(3.9, 16, true));
    }

    @Test
    void scholarship_gpaOk_but_credits14_falls_to70() {
        assertEquals(70, calc.scholarship(3.9, 14, false));
    }

    @Test
    void scholarship_gpa3point5_credits16_is70() {
        assertEquals(70, calc.scholarship(3.5, 16, false));
    }

    @Test
    void scholarship_gpa3point2_credits16_is50() {
        assertEquals(50, calc.scholarship(3.2, 16, false));
    }

    @Test
    void scholarship_gpa3point5_credits10_falls_to50() {
        assertEquals(50, calc.scholarship(3.5, 10, false));
    }

    @Test
    void scholarship_lowGpa_is0() {
        assertEquals(0, calc.scholarship(2.5, 16, false));
    }

    // ===================== C: Давталтын заагийн тест 0/1/2+ (commit 3) =====================

    @Test
    void gpa_emptyArray_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(new double[]{}));
    }

    @Test
    void gpa_null_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(null));
    }

    @Test
    void gpa_oneScore_is4() {
        assertEquals(4.0, calc.gpa(new double[]{95}), 0.0001);
    }

    @Test
    void gpa_manyScores_is2point5() {
        assertEquals(2.5, calc.gpa(new double[]{95, 85, 75, 65}), 0.0001);
    }

}
