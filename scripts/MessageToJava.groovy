println "Running MessageToJava ${args[0]} ${args[1]}"

def input = new File(args[0])

def message = null
def fields = []

input.eachLine {
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

def out = new File(args[1] + "/" + message + ".java")
out.delete()

println "Creating file $out..."

out << "public class $message {\n"
fields.each {
	out << "\tprivate String $it;\n"
}
out << "}\n"

println "File $out created!"
