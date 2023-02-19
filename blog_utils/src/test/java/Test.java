import com.teak.blog.result.GlobalResult;

import java.util.HashMap;

/**
 * The type Test.
 *
 * @author 柚mingle木
 * @version 1.0
 * @date 2023 /2/19
 */
public class Test {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        HashMap<String, Object> hashMap = new HashMap<>(1);
        hashMap.put("成功", "哈哈");
        HashMap<String, Object> hashMap1 = new HashMap<>(1);
        hashMap.put("失败", "呜呜");

        GlobalResult globalResult = GlobalResult.globalResult();
        System.out.println("globalResult.hashCode() = " + globalResult.hashCode());
        System.out.println("======================================================== ");

        GlobalResult ok = globalResult.ok(hashMap);
        System.out.println("ok.hashCode() = " + ok.hashCode());
        System.out.println("======================================================== ");

        GlobalResult globalResult1 = GlobalResult.globalResult();
        System.out.println("globalResult1.hashCode() = " + globalResult1.hashCode());
        System.out.println("======================================================== ");

        GlobalResult fail = globalResult1.fail(hashMap1);
        System.out.println("fail.hashCode() = " + fail.hashCode());
        System.out.println("======================================================== ");

        System.out.println("ok.hashCode() = " + ok.hashCode());
        System.out.println("globalResult.hashCode() = " + globalResult.hashCode());

        System.out.println("globalResult.getMessage() = " + globalResult.getMessage());

    }
}
