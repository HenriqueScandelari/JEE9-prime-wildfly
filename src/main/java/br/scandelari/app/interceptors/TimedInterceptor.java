package br.scandelari.app.interceptors;

import br.scandelari.app.interceptors.annotations.Timed;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Interceptor
@Timed
public class TimedInterceptor {

    @AroundInvoke
    public Object timeInvocation(InvocationContext ctx) throws Exception {
        long start = System.currentTimeMillis();
        Object result = ctx.proceed();
        long finish = System.currentTimeMillis();
        System.out.printf("Method %s took %d ms.%n", ctx.getMethod().getName(), finish - start);
        return result.toString() + " Method: "  + ctx.getMethod().getName() + " took " +  (finish - start) + "ms";
    }

}
