package pe.forjix.leccion06;

public record Student(String name, int score) {
    public Student {
        if (score < 0 || score > 20) {
            throw new IllegalArgumentException("Invalid grade: " + score);
        }
        if (name.isBlank() || name.isEmpty()) {
            throw new IllegalArgumentException("name is mandatory");
        }
        name = capitalizeNames(name);
    }

    String capitalizeNames(String fullName) {
        final String[] words = fullName.strip().toLowerCase().split("\\s+");
        final StringBuilder result = new StringBuilder();
        for (final String word : words) {
            if (!result.isEmpty()) {
                result.append(' ');
            }
            result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
        }
        return result.toString();
    }

    boolean isPassing() {
        return score >= 11;
    }
}
