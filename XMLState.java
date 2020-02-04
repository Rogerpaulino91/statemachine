public enum XMLState {

	DEFAULT("Default", "Default handler - writes the data to the output stream"), //
	CAPTURE_START_TAG("Capture Start Tag", "Capture and process the start tag info"), //
	UNCOMPRESS("Uncompress", "Uncompress the bytes in the input stream");

	private final String name;
	private final String description;

	XMLState(String name, String description) {
		this.name = name;
		this.description = description;
	}

	@Override
	public String toString() {

		return "XmlState: " + name + " - " + description;
	}

}