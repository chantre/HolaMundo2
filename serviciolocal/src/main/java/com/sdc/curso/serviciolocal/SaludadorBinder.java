package com.sdc.curso.serviciolocal;

import android.os.Binder;

/**
 * Created by mañá on 09/02/2015.
 */
//   a esto tb se le llama wrapper
public class SaludadorBinder extends Binder implements ISaludadorService {
    //  implementaremos los métodos que nos interesen
    //  el Binder es el intermediario y debe conocer al servicio
    //  por tanto, inyección. Lo podemos hacer por constructor
    private SaludadorService servicio;

    public SaludadorBinder(SaludadorService servicio) {
        this.servicio = servicio;
    }

    @Override
    public String saludar(String nombre) {
        return servicio.saludar(nombre);
    }

    @Override
    public void despedir(String nombre) {
        servicio.despedir(nombre);
    }


}
