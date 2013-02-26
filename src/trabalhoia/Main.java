/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trabalhoia;

import trabalhoia.algoritmos.HillClimbingSearch;
import trabalhoia.estruturas.Function;
import trabalhoia.estruturas.Problem;
import static java.lang.Math.*;


/**
 *
 * @author rodrigo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Function funcaoQuadrada = new Function() {

            @Override
            public double value(double x) {
                return (pow(x, 3.0) * -1.0) + 5.0 * pow(x, 2.0) + 2.0;
            }
        };
        Problem problemaFuncaoQuadrada = new Problem(funcaoQuadrada, -15.0, -20.0, -10.0, 0.1);
        HillClimbingSearch search = new HillClimbingSearch(problemaFuncaoQuadrada);
        System.out.println("O máximo da função f(x) = -x³ + 5x² + 2 é: " + search.getLocalMax());
    }

}
