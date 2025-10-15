package seedu.duke;

public class ScoreCommand extends Command {
    private final String input;

    public ScoreCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(UI ui, ModuleList modules) throws UniflowException {
        if (input == null || input.trim().isEmpty()) {
            throw new UniflowException("Please provide scores in a/b format, e.g., 10/10 15/20 25/30 30/40");
        }

        String[] tokens = input.trim().split(" ");
        if (tokens.length == 0) {
            throw new UniflowException("No scores provided");
        }

        int earnedScore = 0;
        int possibleScore = 0;

        for (String token : tokens) {
            if (token.isBlank()) {
                continue;
            }
            String[] pair = token.split("/");
            if (pair.length != 2) {
                throw new UniflowException("Invalid token: \"" + token + "\" (use a/b format)");
            }

            int userScore;
            int maxScore;

            try {
                userScore = Integer.parseInt(pair[0]);
                maxScore = Integer.parseInt(pair[1]);
            } catch (NumberFormatException e) {
                throw new UniflowException("Scores must be integers: \"" + token + "\"");
            }

            if (userScore < 0 || maxScore <= 0 || userScore > maxScore) {
                throw new UniflowException("Each a/b must satisfy 0 ≤ a ≤ b and b > 0. Offender: \"" + token + "\"");
            }

            earnedScore += userScore;
            possibleScore += maxScore;
        }

        if (possibleScore != 100) {
            throw new UniflowException("Total possible score must add up to 100 (got " + possibleScore + ").");
        }

        double percentScore = earnedScore;
        ui.showMessage(String.format("Total: %.2f%% (%d/100)", percentScore, earnedScore));
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
