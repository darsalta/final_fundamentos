 cat README.md | pandoc -f markdown -t latex | sed 's/\\includegraphics{http[^}]*}//' | pandoc -f latex -o README.pdf