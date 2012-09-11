println "Running MessageToJava ${args[0]} ${args[1]}"


def processMessage(messageFile) {
	def message = null
	def pkg = ""
	def fields = []

	def lastDot = messageFile.name.lastIndexOf('.')

	if(lastDot >= 0) {
		message = messageFile.name.substring(lastDot)
		pkg = messageFile.name.substring(0, lastDot)
	} else {
		message = messageFile.name
	}

	messageFile.eachLine {
		if (it.startsWith("#")) {
			println it
		} else if (it.startsWith("M")) {
			message = it - "M "
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
	out.delete()

	println "Creating file $out..."

	out << "package $pkg;\n\n"
	out << "public class $message {\n"
	fields.each {
		out << "\tprivate String $it;\n"
	}
	out << "}\n"

	println "File $out created!"
}

new File(args[0]).eachFile {
	processMessage(it)
}