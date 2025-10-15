package seedu.duke;

public class ScoreCommand extends Command {
    private final String input;

    public ScoreCommand(String input) {
        this.input = input;
    }


    @Override
    public void execute(UI ui) throws UniflowException {
        String[] tokens = input.split(" ");
        if (tokens.length == 0) {
            throw new UniflowException("No scores provided");
        }

        int earnedScore = 0;
        int possibleScore = 0;

        for (String token : tokens) {
            String[] pair = token.split("/");
            if (pair.length != 2) {
                throw new UniflowException("Invalid token: \"" + token + "\"");
            }
            
            int userScore;
            int maxScore;
            
            try {
                userScore = Integer.parseInt(pair[0]);
                maxScore = Integer.parseInt(pair[1]);
            } catch (NumberFormatException e) {
                throw new UniflowException("Scores must be integers");
            }
            if (userScore < 0 || maxScore <= 0 || userScore > maxScore) {
                throw new UniflowException("Scores must be between 0 and the max score");
            }
            earnedScore += userScore;
            possibleScore += maxScore;
        }

        if (possibleScore != 100) {
            throw new UniflowException("Total possible score must add up to 100");
        }

        double percentScore = (earnedScore * 100.0) / possibleScore;
        ui.showMessage("Score: " + percentScore);
    }

    @Override
    public boolean isExit() {
        return true;
    }
}


