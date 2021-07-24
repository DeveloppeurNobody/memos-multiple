package apps_spring.aop.metier_spring;

import apps_spring.aop.metier_spring.aspects.Log;
import apps_spring.aop.metier_spring.aspects.SecuredByAspect;

import org.springframework.stereotype.Service;

@Service
public class MetierImpl implements IMetier{

    @Override
    @Log
    @SecuredByAspect(roles={"USER", "ADMIN"})
    public void process() {
        System.out.println("Business Process....");
        
    }

    @Override
    @SecuredByAspect(roles={"ADMIN"})
    public double compute() {
        double data = 78;
        System.out.println("Busines Computing and returning");
        return data;
    }
    
}
