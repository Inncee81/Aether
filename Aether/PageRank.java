import java.util.HashMap;
import java.util.LinkedList;

import Jama.Matrix;
import java.util.Random;

public class PageRank {
    private Website[] websites;
    private double d; //Damping Factor / Teleport --Value from 0-1
    private double[][] H;

    public PageRank(Website[] websites) {
        this.websites = websites;
        this.d = .85;
        this.H = new double[this.websites.length][this.websites.length];
        this.ranks = this.getRanks();
    }

    public void calculateRanks() {
        for (int i=0; i<this.websites.length; i++) {
            for(int j=0; j<this.websites.length; j++) {
                if (i==j) this.H[j][i] = 1; 
                else if (this.websites[j].getB().containsKey(i)) this.H[j][i] = -this.d/this.websites[i].getL();
                else this.H[j][i] = 0;
            }
        }
        this.printMatrix(this.H);
        this.H = new Matrix(this.H).inverse().getArray();

        this.printMatrix(this.H);

        for (int i=0; i<this.websites.length; i++) {
            double rankI = 0;
            for(int j=0; j<this.websites.length; j++) {
                rankI += this.H[i][j];
            }
            this.websites[i].setRank(rankI*(1-this.d));
        }
    }

    private void printMatrix(double[][] matrix) {
        for (int i=0; i<matrix.length; i++) {
            for(int j=0; j<matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ,");
            } System.out.println();
        }
    }

    public LinkedList<Double> getRanks() {
        LinkedList<Double> ranksTemp = new LinkedList<>();
        return ranksTemp;
    }



    public static void main(String[] args) {
        // HashMap<Integer,Integer> B1 = new HashMap<>(),B2 = new HashMap<>(),B3 = new HashMap<>(),B4 = new HashMap<>(),B5 = new HashMap<>();

        // B2.put(0, 1);
        // B3.put(1, 1);
        // B4.put(4, 1);
        // B5.put(0, 1);
        // B5.put(1, 1);
        // B5.put(2, 1);
        // B5.put(3, 1);

        // Website[] websites = {new Website(2, B1,0),new Website(2, B2,1),new Website(1, B3,2),new Website(1, B4,3),new Website(1, B5,4)};
    //     int init_size = 10000;
    //     Website[] websites = new Website[init_size];
    //     Random r = new Random();

    //     for(int i=0; i < init_size; i++){
    //         HashMap<Integer,Integer> Bi = new HashMap<>();
    //         int bSize = r.nextInt(init_size/2);
    //         //System.out.println("bsize: "+bSize);
    //         for (int j=0; j < bSize; j++) {
    //             int id_link_from = r.nextInt(init_size);
    //             //System.out.println("id ext: " + id_link_from);
    //             Bi.put(id_link_from, 1);
    //         }
    //         int random_l = r.nextInt(init_size);
    //         //System.out.println("random l: "+random_l);
    //         websites[i] = new Website(random_l,Bi,i);
    //         System.out.println("-----------------------------------");
    //     }



        HashMap<Integer,Integer> B0 = new HashMap<>(),B1 = new HashMap<>(),B2 = new HashMap<>();

        B0.put(2, 1);
        B1.put(0, 1);
        B1.put(2, 1);
        B2.put(0, 1);
       
        Website[] websites = {new Website(2, B0,0),new Website(1, B1,1),new Website(2, B2,2)};


        PageRank pg = new PageRank(websites);
        pg.calculateRanks();

        for(Website ws : pg.websites) {
            System.out.println("Rank of page "+ws.getId()+": "+ws.getRank());
        }
    }

}