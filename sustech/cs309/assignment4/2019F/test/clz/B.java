package clz;

public class B {

    private C cDep;
    private D dDep;

    public B(C cDep, D dDep) {

        this.cDep = cDep;
        this.dDep = dDep;
    }

    public C getCDep() {
        return cDep;
    }

    public D getDDep() {
        return dDep;
    }
}
