import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import com.sun.org.apache.xml.internal.utils.StopParseException;

public class Main {

	public static String[] stopwords = new String[] {
	/* english stop */"a", "about", "above", "above", "across", "after",
			"afterwards", "again", "against", "all", "almost", "alone",
			"along", "already", "also", "although", "always", "am", "among",
			"amongst", "amoungst", "amount", "an", "and", "another", "any",
			"anyhow", "anyone", "anything", "anyway", "anywhere", "are",
			"around", "as", "at", "back", "be", "became", "because", "become",
			"becomes", "becoming", "been", "before", "beforehand", "behind",
			"being", "below", "beside", "besides", "between", "beyond", "bill",
			"both", "bottom", "but", "by", "call", "can", "cannot", "cant",
			"co", "con", "could", "couldnt", "cry", "de", "describe", "detail",
			"do", "done", "down", "due", "during", "each", "eg", "eight",
			"either", "eleven", "else", "elsewhere", "empty", "enough", "etc",
			"even", "ever", "every", "everyone", "everything", "everywhere",
			"except", "few", "fifteen", "fify", "fill", "find", "fire",
			"first", "five", "for", "former", "formerly", "forty", "found",
			"four", "from", "front", "full", "further", "get", "give", "go",
			"had", "has", "hasnt", "have", "he", "hence", "her", "here",
			"hereafter", "hereby", "herein", "hereupon", "hers", "herself",
			"him", "himself", "his", "how", "however", "hundred", "ie", "if",
			"in", "inc", "indeed", "interest", "into", "is", "it", "its",
			"itself", "keep", "last", "latter", "latterly", "least", "less",
			"ltd", "made", "many", "may", "me", "meanwhile", "might", "mill",
			"mine", "more", "moreover", "most", "mostly", "move", "much",
			"must", "my", "myself", "name", "namely", "neither", "never",
			"nevertheless", "next", "nine", "no", "nobody", "none", "noone",
			"nor", "not", "nothing", "now", "nowhere", "of", "off", "often",
			"on", "once", "one", "only", "onto", "or", "other", "others",
			"otherwise", "our", "ours", "ourselves", "out", "over", "own",
			"part", "per", "perhaps", "please", "put", "rather", "re", "same",
			"see", "seem", "seemed", "seeming", "seems", "serious", "several",
			"she", "should", "show", "side", "since", "sincere", "six",
			"sixty", "so", "some", "somehow", "someone", "something",
			"sometime", "sometimes", "somewhere", "still", "such", "system",
			"take", "ten", "than", "that", "the", "their", "them",
			"themselves", "then", "thence", "there", "thereafter", "thereby",
			"therefore", "therein", "thereupon", "these", "they", "thickv",
			"thin", "third", "this", "those", "though", "three", "through",
			"throughout", "thru", "thus", "to", "together", "too", "top",
			"toward", "towards", "twelve", "twenty", "two", "un", "under",
			"until", "up", "upon", "us", "very", "via", "was", "we", "well",
			"were", "what", "whatever", "when", "whence", "whenever", "where",
			"whereafter", "whereas", "whereby", "wherein", "whereupon",
			"wherever", "whether", "which", "while", "whither", "who",
			"whoever", "whole", "whom", "whose", "why", "will", "with",
			"within", "without", "would", "yet", "you", "your", "yours",
			"yourself", "yourselves", "the",
			/* html attributes */"abbr", "accept", "accesskey", "action",
			"align", "alink", "alt", "archive", "axis", "background",
			"bgcolor", "border", "cellpadding", "cellspacing", "char",
			"charoff", "charset", "checked", "cite", "class", "classid",
			"clear", "code", "codebase", "codetype", "color", "cols",
			"colspan", "compact", "content", "coords", "data", "datetime",
			"declare", "defer", "dir", "disabled", "enctype", "face", "for",
			"frame", "frameborder", "headers", "height", "href", "hreflang",
			"hspace", "htt", "id", "ismap", "label", "lang", "language",
			"link", "longdesc", "marginheight", "marginwidth", "maxlength",
			"media", "method", "multiple", "name", "nohref", "noresize",
			"noshade", "nowrap", "object", "onblur", "onchange", "onclick",
			"ondblclick", "onfocus", "onkeydown", "onkeypress", "onkeyup",
			"onload", "onmousedown", "onmousemove", "onmouseout",
			"onmouseover", "onmouseup", "onreset", "onselect", "onsubmit",
			"onunload", "profile", "prompt", "readonly", "rel", "rev", "rows",
			"rowspan", "rules", "scheme", "scope", "scrolling", "selected",
			"shape", "size", "span", "src", "standby", "start", "style",
			"summary", "tabindex", "target", "text", "title", "type", "usemap",
			"valign", "value", "valuetype", "version", "vlink", "vspace",
			"width",
			/* html elements */"a", "abbr", "acronym", "address", "applet",
			"area", "article", "aside", "audio", "b", "base", "basefont",
			"bdi", "bdo", "bgsound", "big", "blink", "blockquote", "body",
			"br", "button", "canvas", "caption", "center", "cite", "code",
			"col", "colgroup", "command", "content", "data", "datalist", "dd",
			"del", "details", "dfn", "dialog", "dir", "div", "dl", "dt",
			"element", "em", "embed", "fieldset", "figcaption", "figure",
			"font", "footer", "form", "frame", "frameset", "head", "header",
			"hgroup", "hr", "html", "i", "iframe", "image", "img", "input",
			"ins", "isindex", "kbd", "keygen", "label", "legend", "li", "link",
			"listing", "main", "map", "mark", "marquee", "menu", "menuitem",
			"meta", "meter", "multicol", "nav", "nobr", "noembed", "noframes",
			"noscript", "object", "ol", "optgroup", "option", "output", "p",
			"param", "picture", "plaintext", "pre", "progress", "q", "rp",
			"rt", "rtc", "ruby", "s", "samp", "script", "section", "select",
			"shadow", "small", "source", "spacer", "span", "strike", "strong",
			"style", "sub", "summary", "sup", "table", "tbody", "td",
			"template", "textarea", "tfoot", "th", "thead", "time", "title",
			"tr", "track", "tt", "u", "ul", "var", "video", "wbr", "xmp" };

	public static Dictionary dict;
	public static int numOfClassifications = 0;
	public static int MAXCLASSIFICATION = 50;

	public static boolean isStopWord(String str) {

		for (int i = 0; i < stopwords.length; i++) {
			if (stopwords[i].equals(str))
				return true;
		}
		return false;
	}

	public static void main(String argv[]) throws Exception {

		// command line validation
		if (argv.length != 5) {
			System.err
					.println("input error: usage example: tree_learn traindir/ test.examples 25 7 output.txt");
			System.err.printf("argv length: %d\n", argv.length);
			System.exit(-1);
		}

		// get training set and test files
		File trainDir = new File(argv[0]);
		File[] dirents = trainDir.listFiles();
		ArrayList<File> trainingSetFiles = new ArrayList<File>();
		ArrayList<File> files = new ArrayList<File>();
		File testExamples = null;
		File testLabels = null;
		for (int i = 0; i < dirents.length; i++) {
			File dirent = dirents[i];
			if (dirent.getName().contains(".train")) {
				trainingSetFiles.add(dirent);
			}
			if (dirent.getName().contains(".examples")) {
				testExamples = dirent;
			}
			if (dirent.getName().contains(".labels")) {
				testLabels = dirent;
			}
		}
		numOfClassifications = trainingSetFiles.size();
		for (int i = 0; i < numOfClassifications; i++) {
			files.add(trainingSetFiles.get(i));

		}
		if (testExamples == null) {
			testExamples = new File(argv[1]);
		}
		files.add(testExamples);
		double validationPercantage = Double.parseDouble(argv[2]) / 100;
		int L = Integer.parseInt(argv[3]);
		File output = new File(argv[4]);
		if (!output.exists()) {
			output.createNewFile();
		}

		// read all files and build dictionary
		System.out.println("reading files from disk...");
		FileWriter fw = new FileWriter(output.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		// bw.write(new Date().toString() + "\n");
		// bw.write("Learning results:\n");
		System.out.println("reading files done");
		System.out.println("Building dictionary...");
		dict = new Dictionary(files);
		System.out.println("Dictionary complete");

		// create messages objects from all files (test also)(for learning and
		// validation)
		System.out.println("(improved)Parsing Messages...");
		ArrayList<Message> messages = Parser.CreateMessages(files, testLabels);

		// divide to testing and learning messages:
		ArrayList<Message> testingMessages = new ArrayList<Message>();
		Message temp = null;
		for (int i = 0; i < messages.size(); i++) {
			temp = messages.get(i);
			if (temp.isForTesting()) {
				testingMessages.add(temp);
			}
		}
		for (int i = 0; i < testingMessages.size(); i++) {
			messages.remove(testingMessages.get(i));
		}

		// move some of training messages to be validation messages (according
		// to validationPercantage parameter)
		ArrayList<Message> validationMessages = new ArrayList<Message>();
		int numOfValidation = (int) Math.floor(messages.size()
				* validationPercantage);
		for (int i = 0; i < numOfValidation; i++) {
			int rand = (int) Math.floor(Math.random() * (messages.size()));
			temp = messages.get(rand);
			validationMessages.add(temp);
		}

		for (int i = 0; i < validationMessages.size(); i++) {
			messages.remove(validationMessages.get(i));
		}
		System.out.println("Parsing done");

		// build trees in different sizes and test them on the validation
		// messages
		System.out.println("Building Tree");
		Tree bestTree = null;
		Tree currentTree = null;
		double bestTreeResult = 0;
		for (int T = (int) Math.pow(2, 0); T <= Math.pow(2, L); T *= 2) {
			currentTree = new Tree(messages, T);
			double resultOnValidation = currentTree.testResults(
					validationMessages, null);// null = don't write results to
												// file.
			double resultOnTraining = currentTree.testResults(messages, null);
			System.out
					.printf("Tree Size: %d; Success Rate: validation: %f, training: %f\n",
							T, resultOnValidation, resultOnTraining);
			if (resultOnValidation >= bestTreeResult) {
				bestTree = currentTree;
				bestTreeResult = resultOnValidation;
			}
		}
//		TreePrinter.print(bestTree.getRoot());
		// print results on screen and to output file
		System.out.printf("Best Tree size (on validation): %d\n",
				bestTree.getSize());
		System.out.println("testing the best tree on the test examples");
		double result = bestTree.testResults(testingMessages, bw);

		System.out.printf("Success Rate: %f\n", result);
		System.out.println("------------- First ten words --------------");
		bestTree.printTenWords();

		bw.close();

		System.exit(0);

	}

}
