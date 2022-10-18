package com.leyvadev.iesjrecontrol.logincontrol.repositories;

import com.leyvadev.iesjrecontrol.logincontrol.model.Radcheck;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RadcheckRepository implements PanacheRepositoryBase<Radcheck, Long> {

}

