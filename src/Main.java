import java.util.HashMap;
import com.hesamian.ring.Ring;

public class Main {

    public static void main(String[] args) {

        System.out.println(new Ring("2+16/2^4").evaluate(new HashMap()));
        System.out.println(new Ring("2+16/2^4*2").evaluate(new HashMap()));
        System.out.println(new Ring("2+16/2^4*2+(3-1*3-4^4^5)").evaluate(new HashMap()));
        System.out.println(new Ring("4^4^5").evaluate(new HashMap()));
        System.out.println(new Ring("4^4^5*3/3-1^4^5").evaluate(new HashMap()));
        System.out.println(new Ring("4^4^5*3/3-1^(4^5)").evaluate(new HashMap()));

    }
}
