import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MergeSort {
    static int numVotes = 3;
    int voters;
    int candidates;
    int [][] votes;
    int validVotes;
    boolean secondRound;
    int[] idCandidates;
    List<Candidate> candidatesVotes = new ArrayList<Candidate>();

    public class Candidate implements Comparable<Candidate>{
        int Id;
        int votes;

        public Candidate(int id, int votes){
            this.Id = id;
            this.votes = votes;
        }

        @Override
        public int compareTo(Candidate other) {
            return other.votes - this.votes; 
        }
    }

    public void FillVotes(Scanner scanner){
        int i = 0, j = 0; 
        this.secondRound = false;        
        votes = new int[voters][numVotes];

        for(i = 0; i < voters; i++){
            for(j = 0; j < numVotes; j++){
                votes[i][j] = scanner.nextInt();
            }
        }
    }

    public void RecordVotes(){
        this.validVotes = 0;  

        for(int i = 0; i < voters; i++){
            for(int j = 0; j < numVotes; j++){
                if(ValidateVote(i, j)){
                    AddVote(votes[i][j]);
                    if(secondRound) break;
                }
            }
        }
    }

    public boolean ValidateVote(int i, int j){
        if(secondRound){
            if(votes[i][j] == idCandidates[0] || votes[i][j] == idCandidates[1]) return true;
            else return false;
        }
        else if(j == 0) return true;
        else return false;
    }

    public void CreateCandidates(){
        for(int i = 0; i < candidates; i++){
            candidatesVotes.add(new Candidate(i+1, 0));
        }
    }

    public void AddVote(int id){
        for(Candidate can : candidatesVotes){
            if(can.Id == id){
                can.votes++;
                validVotes++;
            }
        }
    }

    public String getWinner(){
        StringBuilder results = new StringBuilder();

        Collections.sort(candidatesVotes);
        Candidate winner = candidatesVotes.get(0);

        double percentage = getPercentage(winner.votes);
        results.append(winner.Id + " " + String.format("%.2f", percentage));

        if(percentage < 50){
            Candidate newWinner = getWinnerSecondRound();
            percentage = getPercentage(newWinner.votes);
            results.append("\n" + newWinner.Id + " " + String.format("%.2f", percentage));
        }

        return results.toString();
    }

    public Candidate getWinnerSecondRound(){
        candidatesVotes.get(0).votes = 0;
        candidatesVotes.get(1).votes = 0;

        idCandidates = new int[2];
        idCandidates[0] = candidatesVotes.get(0).Id;
        idCandidates[1] = candidatesVotes.get(1).Id;

        this.secondRound = true;
        RecordVotes();
        
        Collections.sort(candidatesVotes);
        return candidatesVotes.get(0);
    }

    public double getPercentage(int votes){
        return ((double)votes / (double)validVotes) * 100;
    }

    public static void main(String[] args){
        MergeSort sort = new MergeSort();
        Scanner scanner = new Scanner(System.in);
        sort.voters = scanner.nextInt();
        sort.candidates = scanner.nextInt();

        sort.CreateCandidates();
        sort.FillVotes(scanner);
        scanner.close();
        sort.RecordVotes();
       
        if(sort.validVotes == 0){
            System.out.println(0);
        }
        else{
            System.out.println(sort.getWinner());
        }
    }

}