/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.sab.wlsjms.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author sbutton
 */
@TraceMethod
@Interceptor
/** 
 * Display method calls as they occur
 */
public class TraceMethodImpl {
    
    @AroundInvoke 
    public Object logMethod(InvocationContext ctx) throws Exception { 
        // Print the target details
        System.out.printf("\n*** Target: %s::%s\n",
                ctx.getTarget().toString(),
                ctx.getMethod().getName());
        return ctx.proceed();
    }
}
