package com.company;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;



public class Main {

    int[] solution;
    HashMap<Integer, List<Integer>> candidates;

    public static void main(String args[]) {
        Main main = new Main();
        main.solution = new int[10];//шешімді сақтайды; 0 индексі қолданылмайды, мен 1-ден 9-ға дейінгі индекстерді қолданамын
        main.candidates = new HashMap<Integer, List<Integer>>();//for each position (1 to 9) in the solution, stores a list of candidate elements for that position

        List<Integer> oneToNine = new LinkedList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        /*
         * ешқандай шешім 2,4,6 немесе 8 матрицалық элементтерден басталуы мүмкін емес,
         * оңтайландыру мақсатында жоғарыдағы тізім келесідей жазылуы мүмкін
         *Arrays.asList(1,3,5,7,9)
         * дәл қазір бұл қалай жүреді, бағдарламаның қалай жұмыс істейтінін бақылау пайдалы
         * кездейсоқ 2,4,6 немесе 8-ден басталған кезде қайтарымды орындай ма
         */
        Collections.shuffle(oneToNine);//тізімді кездейсоқ өзгерту
        main.candidates.put(1, oneToNine);
        main.buildSol(1);

    }

    //шегіну
    public void buildSol(int k)
    {
        if(k==10)
        {
            System.out.print("шешім келесідей ");
            printSolution();
            return;
        }

        List<Integer> candList = candidates.get(k);
        if(candList.isEmpty())
        {
            cleanSolution(k);
            buildSol(k-1); //егер ағымдағы қадамға үміткерлер болмаса, артқа бір қадам жасаңыз
        }
        else
        {
            int firstCandidate = candList.get(0);
            candList.remove(0);
            candidates.put(k, candList);
            solution[k] = firstCandidate;//шешімдегі k позициясы үшін үміткерлер тізіміндегі бірінші элементті таңдаңыз

            List<Integer> neighbors = getNeighbors(solution[k]);
            List<Integer> prevElems = getPreviousElementsInSolution(k);
            candidates.put(k+1, generateCandidates(neighbors, prevElems));//k қадамында k+1 қадамына үміткер элементтер жасаңыз
            // бұл үміткерлер ағымдағы элементтің көршілері (матрицада) ([k] шешімі),
// бұл бұрынғы позициядағы шешімнің бөлігі емес

            System.out.println("step "+k);
            System.out.print("іргелес шешім: ");
            printSolution();
            System.out.println();


            buildSol(k+1);
        }
    }



    //кандидаттар-бұл көршілер болып табылатын және бұрын қатыспаған элементтер
    public List<Integer> generateCandidates(List<Integer> neighbors, List<Integer> previousElements)
    {
        List<Integer> cnd = new ArrayList<Integer>();
        for(int i=0;i<neighbors.size();i++)
            if(!previousElements.contains(neighbors.get(i)))
                cnd.add(neighbors.get(i));

        return cnd;
    }

    //[k]шешіміне дейін шешімдегі алдыңғы элементтер жиынтығын алыңыз
    public List<Integer> getPreviousElementsInSolution(int step)
    {
        List<Integer> previousElements = new ArrayList<Integer>();
        for(int i=1; i<=step-1;i++)
            previousElements.add(solution[i]);

        return previousElements;
    }

    //шешімге сәйкес келетін матрица элементінің көршілерін алыңыз [k]

    public  List<Integer> getNeighbors(int element) {

        List<Integer> neighboursList = new ArrayList<Integer>();

        switch (element) {

            case 1: neighboursList = Arrays.asList(2, 4);
                break;

            case 2: neighboursList = Arrays.asList(1, 3, 5);
                break;

            case 3: neighboursList = Arrays.asList(2, 6);
                break;

            case 4: neighboursList = Arrays.asList(1, 5, 7);
                break;

            case 5: neighboursList = Arrays.asList(2, 4, 6, 8);
                break;

            case 6: neighboursList = Arrays.asList(3, 5, 9);
                break;

            case 7: neighboursList = Arrays.asList(4, 8);
                break;

            case 8: neighboursList = Arrays.asList(5, 7, 9);
                break;

            case 9: neighboursList = Arrays.asList(6, 8);
                break;

            default: neighboursList = new ArrayList<Integer>();
                break;
        }

        return neighboursList;
    }



    public void printSolution()
    {
        for(int i=1;i<solution.length;i++)
            System.out.print(solution[i]+" ");
    }

    public void cleanSolution(int k)
    {
        for(int i=k;i<solution.length;i++)
            solution[i]=0;
    }
}