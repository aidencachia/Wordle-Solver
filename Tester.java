public class Tester {
    public static void main(String[] args) {
        int totalRuns = 20000;
        double totalWordChecks = 0.00;

        int percentage;

        for (int i = 0; i < totalRuns; i++) {
            percentage = i/(totalRuns/100);
            
            if(i % (totalRuns/100) == 0){
                System.out.println("["+"█".repeat(percentage) + "░".repeat(100-percentage)+"]\t" + percentage + "%");
            }
            totalWordChecks += WordleSimulator.runGame();
        }
        
        System.out.println("Average number of word checks: " + totalWordChecks/totalRuns);
    }
}
