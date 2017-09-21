# magicCube
This is an android Rubik's Cube including AutoRotate,Next Step Tip,RandomDisturb,Step Count.
# formula 
This Rubik's Cube uses some LayerFist and CFOP formulas
# formula code
    public static void fishOne(List<Integer> steps) {
        _R(steps);
        _U(steps);
        R(steps);
        _U(steps);
        _R(steps);
        _U2(steps);
        R(steps);
    }
        public static void PLL1(List<Integer> steps) {
        R(steps);
        _U(steps);
        R(steps);
        U(steps);
        R(steps);
        U(steps);
        R(steps);
        _U(steps);
        _R(steps);
        _U(steps);
        R2(steps);
    }
