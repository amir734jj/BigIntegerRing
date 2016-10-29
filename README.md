# BigInteger Ring
Polynomial ring using BigInteger library: this library evaluate the following correctly (order of operation is preserved):

    Ring ring1 = new Ring("x^2 + 23 *3y + x^3y^z");
    Ring ring2 = new Ring("x^2 + 23 *3y + x*3y+z");
    Ring ring3 = new Ring("x^2 + (23 *3y) + x^3y^z");
    Ring ring4 = new Ring("x^2 + (23 *(2 + x)*3y)+z");
    Ring ring5 = new Ring("2+16/2^4");
    Ring ring6 = new Ring("2+16/2^4*2");
    Ring ring7 = new Ring("2+16/2^4*2+(3-1*3-4^4^5)");
    Ring ring8 = new Ring("4^4^5");
    Ring ring9 = new Ring("4^4^5*3/3-1^4^5");
    Ring ring10 = new Ring("4^4^5*3/3-1^(4^5)");
