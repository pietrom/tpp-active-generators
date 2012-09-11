println "Running MessageToJava ${args[0]} ${args[1]}"

def createJavaClass(out, pkg, message, fields) {
	out.delete()

	println "Creating file $out..."

	out << "package $pkg;\n\n"
	out << "public class $message {\n"
	fields.each {
		writeFieldDefinition(out, it)		
	}
	
	fields.each {
		writeAccessorsDefinitionwrite(out, it)		
	}
	out << "}\n"

	println "File $out created!"
}

def writeFieldDefinition(out, field) {
	out << "\tprivate String $field;\n"
}

def writeAccessorsDefinitionwrite(out, field) {
	def capitalizedField = field[0].toUpperCase() + (field.size() > 1 ? field[1..-1] : "")
	out << """
	public void set${capitalizedField}(String val) {
		this.$field = val;
	}
	
	public String get${capitalizedField}() {
		return this.$field;
	}
"""
}


def processMessage(messageFile) {
	def message = null
	def pkg = ""
	def fields = []

	def lastDot = messageFile.name.lastIndexOf('.')

	if(lastDot >= 0) {
		message = messageFile.name.substring(lastDot + 1)
		pkg = messageFile.name.substring(0, lastDot)
	} else {
		message = messageFile.name
	}

	messageFile.eachLine {
		if (it.startsWith("#")) {
			println it
		} else if (it.startsWith("F")) {
			def field = it - "F "
			fields.add(field)
		} else {
			println "Unknow starter char for line $it"
		}
	}

	def outDir = new File(args[1] + '/' + pkg.replace('.', '/'))
	outDir.mkdirs()
	
	def out = new File(outDir, message + ".java")
	
	createJavaClass(out, pkg, message, fields)
}

new File(args[0]).eachFile {
	processMessage(it)
}