rm -f ./content.tex;
ls content/*.tex | awk '{printf "\\input{%s}\n", $1}' > content.tex;
