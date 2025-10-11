package todo;
switch (cmd) {
case "list":
list();
break;
case "add":
add(joinArgs(args, 1));
break;
case "remove":
remove(Integer.parseInt(args[1]));
break;
default:
System.out.println("Unknown command: " + cmd);
}
}


private static String joinArgs(String[] args, int from) {
return String.join(" ", Arrays.copyOfRange(args, from, args.length));
}


private static void list() throws IOException {
if (!Files.exists(DB)) {
System.out.println("No tasks.");
return;
}
List<String> lines = Files.readAllLines(DB);
for (int i = 0; i < lines.size(); i++) {
System.out.printf("%d. %s%n", i + 1, lines.get(i));
}
}


private static void add(String task) throws IOException {
Files.createDirectories(DB.getParent() == null ? Paths.get(".") : DB.getParent());
try (BufferedWriter w = Files.newBufferedWriter(DB, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
w.write(task);
w.newLine();
}
System.out.println("Added: " + task);
}


private static void remove(int index) throws IOException {
if (!Files.exists(DB)) {
System.out.println("No tasks.");
return;
}
List<String> lines = Files.readAllLines(DB);
if (index < 1 || index > lines.size()) {
System.out.println("Invalid index");
return;
}
String removed = lines.remove(index - 1);
Files.write(DB, lines);
System.out.println("Removed: " + removed);
}
}
