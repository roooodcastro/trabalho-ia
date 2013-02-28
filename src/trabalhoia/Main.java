/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trabalhoia;

import trabalhoia.algoritmos.HillClimbingSearch;
import trabalhoia.algoritmos.SimulatedAnnealingSearch;
import trabalhoia.algoritmos.StepestHillClimbingSearch;
import trabalhoia.estruturas.Function;
import trabalhoia.estruturas.Problem;
import static java.lang.Math.*;


public class Main {

    public static void main(String[] args) {
        // Setar para true para os algoritmos imprimirem cada passo de sua execução
        boolean printStepLogs = false;

        // Exemplo de criação de uma função. Para criar uma nova função, basta implementar o método value,
        // que recebe um valor X como entrada e retorna o resultado da função, o f(x).
        Function funcaoCubica = new Function() {

            @Override
            public double value(double x) {
                return (pow(x, 3.0) * -1.0) + 5.0 * pow(x, 2.0) + 2.0;
            }
        };

        // Para cada função deve ser criado um problema, que além da função, pede como parâmetro:
        // O valor X inicial, os valores de mínimo e máximo do intervalo a ser procurado,
        // e por último o valor de tolerância, ou seja, e quão baixa tem que ser a subida ou
        // mudança de energia para que seja considerada que o problema chegou ao final, após
        // esgotar-se todos os caminhos possíveis
        Problem problemaFuncaoCubica = new Problem(funcaoCubica, -14.35, -50.0, 0.0, 0.01);

        // Inicialização de cada algoritmo para o problema dado como exemplo
        HillClimbingSearch hillClimbingSearch = new HillClimbingSearch(problemaFuncaoCubica);
        StepestHillClimbingSearch stepestHillClimbingSearch = new StepestHillClimbingSearch(problemaFuncaoCubica);
        SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch(problemaFuncaoCubica);

        // Configurando os algoritmos para imprimirem ou não os passos
        hillClimbingSearch.setPrintLog(printStepLogs);
        stepestHillClimbingSearch.setPrintLog(printStepLogs);
        simulatedAnnealingSearch.setPrintLog(printStepLogs);


        
        // Pegando os máximos locais de cada algoritmo
        hillClimbingSearch.getLocalMax();
        stepestHillClimbingSearch.getLocalMax();
        simulatedAnnealingSearch.getLocalMax();

        // Pegando os mínimos locais de cada algoritmo
        hillClimbingSearch.getLocalMin();
        stepestHillClimbingSearch.getLocalMin();
        simulatedAnnealingSearch.getLocalMin();
    }
}