package com.fundynamic;

public class StringCalculator {

	public int add(String input) {
		return add(new NumberedString(input));
	}

	private int add(NumberedString input) {
		NumberedString replacedInput = input.returnNewInstanceWithNewLinesReplacedByDelimiter();
		final String[] split = replacedInput.getNumbersAsString();
		if (hasOneArgument(split)) {
			return parseNumber(replacedInput.getInput());
		}
		int total = 0;
		for (String piece : split) {
			final int value = parseNumber(piece);
			if (value < 0) throw new IllegalArgumentException("negatives not allowed");
			if (value < 999) {
				total += value;
			}
		}
		return total;
	}

	private int parseNumber(String input) {
		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	private boolean hasOneArgument(String[] split) {
		return split.length == 0;
	}

	private class NumberedString {

		private static final String SLASHES = "//";
		private static final int INDEX_AFTER_SLASHES = 2;

		private String delimiter = ",";

		private String input;

		public NumberedString(String input) {
			this.input = input;
			if (input.startsWith(SLASHES)) {
				delimiter = input.substring(INDEX_AFTER_SLASHES, getFirstNewLine(input));
				this.input = input.substring(getFirstNewLine(input));
			}
		}

		public NumberedString(String input, String delimiter) {
			this.input = input;
			this.delimiter = delimiter;
		}

		private int getFirstNewLine(String input) {
			return input.indexOf('\n') - 1;
		}

		public NumberedString returnNewInstanceWithNewLinesReplacedByDelimiter() {
			return new NumberedString(input.replaceAll("\n", delimiter), delimiter);
		}

		public String getInput() {
			return input;
		}

		public String[] getNumbersAsString() {
			return input.split(delimiter);
		}
	}

}
