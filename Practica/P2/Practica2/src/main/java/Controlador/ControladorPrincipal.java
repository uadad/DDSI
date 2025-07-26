/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Conexion;
import Modelo.Socio;
import Modelo.SocioDAO;
import Vista.VistaMensaje;
import Vista.vMenu;
import Vista.vSocio;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author wadad
 */
public class ControladorPrincipal {

    public ControladorPrincipal(Conexion con) throws SQLException {
        int opc;
        SocioDAO s = new SocioDAO(con);
        vMenu vmenu = new vMenu();
        vSocio vS = new vSocio();
        VistaMensaje vm = new VistaMensaje();
        Scanner sc = new Scanner(System.in);
        do {
            vmenu.muestraMenu();
            opc = sc.nextInt();

            switch (opc) {
                case 1:
                 try {
                    ArrayList<Socio> lis = s.ListarSocios();
                    vS.muestraSocios(lis);
                } catch (SQLException e) {
                    vm.mensajeConsola("Error en la peticion", e.getMessage());
                }
                break;

                case 2:
                    sc.nextLine();
                    vm.mensajeConsola("Introduce el numero de Socio:");
                    String ns = sc.nextLine();
                    
                    if (!s.ConsultarSocio(ns)) {
                        vm.mensajeConsola("Introduce el dni:");
                        String ds = sc.nextLine();
                        vm.mensajeConsola("Introduce el nombre:");
                        String nom = sc.nextLine();
                        vm.mensajeConsola("Introduce la categoria:");
                        String cat = sc.nextLine();
                       vm.mensajeConsola("Introduce la fecha de entrada:");
                        String fechaE = sc.nextLine();
                        Socio socio = new Socio(ns, nom, ds, fechaE, cat);

                        try {
                            s.AltaSocio(socio);
                        } catch (SQLException e) {
                            vm.mensajeConsola("Error en la peticion", e.getMessage());
                        }
                    } else {
                        vm.mensajeConsola("se encuentra un socio con dicho numero de socio en la bases de datos.");
                    }
                    break;
                    
                case 3:
                    sc.nextLine();
                    vm.mensajeConsola("Introduce el numero de Socio:");
                    ns = sc.nextLine();
                    if (s.ConsultarSocio(ns)) {
                        try {
                            s.BajaSocio(ns);
                        } catch (SQLException e) {
                            vm.mensajeConsola("Error en la peticion", e.getMessage());
                        }
                    } else {
                        vm.mensajeConsola("No se encuentra dicho socio en la bases de datos.");
                    }
                    break;
                case 4:
                    sc.nextLine();
                    vm.mensajeConsola("Introduce el numero de Socio:");
                    ns = sc.nextLine();
                    if (s.ConsultarSocio(ns)) {
                       vm.mensajeConsola("Introduce el Campo que deseas actulizar:");
                        String campo = sc.nextLine();
                       vm.mensajeConsola("Introduce el valor de dicho campo:");
                        String valor = sc.nextLine();

                        try {
                            s.ActualizarSocio(ns, campo, valor);
                        } catch (Exception e) {
                            vm.mensajeConsola("Error en la petición ",e.getMessage());
                        }
                    } else {
                        vm.mensajeConsola("No se encuentra dicho socio en la bases de datos.");
                    }
                    break;
                case 5:
                    break;
                default:
                    vm.mensajeConsola("opción no valida");
            }
        } while (opc != 5);
        sc.close();
    }

}
