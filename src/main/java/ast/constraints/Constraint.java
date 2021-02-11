package ast.constraints;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface Constraint {
	public ConstraintId getID();

	record MaxCon(double value) implements Constraint {
		@Override
		public ConstraintId getID() {
			return ConstraintId.MAX;
		}

		public String validateLength(String text) {
			return text.length() > value ? "Input must contain less than " + (int) value + " characters. " : " ";
		}

		public String validateInt(String text) {
			return text.isEmpty() || new BigInteger(text).compareTo(BigInteger.valueOf((long)value))>0 ?
					"Input must smaller or equals to " + (long) value : " ";
		}

		public String validateDec(String text) {
			return text.isEmpty() || new BigDecimal(text).compareTo(BigDecimal.valueOf((long)value))>0 ?
					"Input must smaller or equals to " + value : " ";
		}
	};

	record MinCon(double value) implements Constraint {
		@Override
		public ConstraintId getID() {
			return ConstraintId.MIN;
		}

		public String validateLength(String text) {
			return text.length() < value ? "Input must contain at least " + (int) value + " characters. " : " ";
		}

		public String validateInt(String text) {
			return text.isEmpty() || "-".equals(text) || new BigInteger(text).compareTo(BigInteger.valueOf((long)value))<0
					? "Input must greater or equals to " + (long) value
					: " ";
		}
		
		public String validateDec(String text) {
			return text.isEmpty() || "-".equals(text) || new BigDecimal(text).compareTo(BigDecimal.valueOf((long)value))<0
					? "Input must greater or equals to " + value
					: " ";
		}
	};

	record MajorTicksCon(int value) implements Constraint {
		@Override
		public ConstraintId getID() {
			return ConstraintId.MAJORTICKS;
		}
	};

	record ApproveCon(String value) implements Constraint {
		@Override
		public ConstraintId getID() {
			return ConstraintId.APPROVE;
		}
	};

	record CancelCon(String value) implements Constraint {
		@Override
		public ConstraintId getID() {
			return ConstraintId.CANCEL;
		}
	};

	record MinorTicksCon(int value) implements Constraint {
		@Override
		public ConstraintId getID() {
			return ConstraintId.MINORTICKS;
		}
	};

	record HolderCon(String value) implements Constraint {
		@Override
		public ConstraintId getID() {
			return ConstraintId.HOLDER;
		}
	};

	record SelectedCon(String value) implements Constraint {
		@Override
		public ConstraintId getID() {
			return ConstraintId.SELECTED;
		}
	};

	record RegexCon(String value) implements Constraint {
		@Override
		public ConstraintId getID() {
			return ConstraintId.REGEX;
		}

		public String validate(String text) {
			return text.matches(value) ? " " : "Input don't match to the pattern '" + value + "'";
		}
	};

	enum OptionalCon implements Constraint {
		Required, Optional;

		public static OptionalCon from(String value) {
			if ("optional".contentEquals(value))
				return OptionalCon.Optional;
			else
				return OptionalCon.Required;
		}

		@Override
		public ConstraintId getID() {
			return ConstraintId.OPTIONAL;
		}

		public String validate(String text) {
			return text == null || text.isEmpty() ? "This field is required" : " ";
		}
	}

}