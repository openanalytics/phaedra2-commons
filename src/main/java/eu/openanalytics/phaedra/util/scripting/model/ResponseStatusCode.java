package eu.openanalytics.phaedra.util.scripting.model;

public enum ResponseStatusCode {

	SUCCESS,
	SCRIPT_ERROR,
	BAD_REQUEST,
	WORKER_INTERNAL_ERROR {
		@Override
		public boolean canBeRetried() {
			return true;
		}
	};

	public boolean canBeRetried() {
		return false;
	}

}