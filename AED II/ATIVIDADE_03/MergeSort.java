import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
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

    public class Candidate {
        int Id;
        int votes;

        public Candidate(int id, int votes){
            this.Id = id;
            this.votes = votes;
        }
    }

    public void FillVotes(int voters){
        this.voters = voters;
        this.secondRound = false;        
        votes = new int[voters][numVotes];

        Scanner scanner = new Scanner(System.in);
        for(int i = 0; i < voters; i++){
            for(int j = 0; j < numVotes; j++){
                votes[i][j] = scanner.nextInt();
            }
        }
        scanner.close();
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

    public void CreateCandidates(int candidates){
        this.candidates = candidates;
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

        candidatesVotes.sort(Comparator.comparing(c -> c.votes));
        Candidate winner = candidatesVotes.get(candidates-1);

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
        candidatesVotes.get(candidates -1).votes = 0;
        candidatesVotes.get(candidates -2).votes = 0;

        idCandidates = new int[2];
        idCandidates[0] = candidatesVotes.get(candidates -1).Id;
        idCandidates[1] = candidatesVotes.get(candidates -2).Id;

        this.secondRound = true;
        RecordVotes();

        return candidatesVotes
        .stream()
        .max(Comparator.comparingInt(c -> c.votes))
        .orElseThrow(NoSuchElementException::new);
    }

    public double getPercentage(int votes){
        return ((double)votes / (double)validVotes) * 100;
    }

    public static void main(String[] args){
        MergeSort sort = new MergeSort();
        //System.out.println("Número de eleitores / numero de candidatos: ");
        Scanner scanner = new Scanner(System.in);
        int voters = scanner.nextInt();
        int candidates = scanner.nextInt();

        sort.CreateCandidates(candidates);
        sort.FillVotes(voters);
        sort.RecordVotes();
        scanner.close();

        System.out.println((sort.validVotes == 0)? 0 : sort.getWinner());
    }

}