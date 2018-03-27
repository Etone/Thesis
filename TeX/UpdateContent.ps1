Write-Output "Updating content.tex"
if (Test-Path "./content.old.tex") {
    Remove-Item ".\content.old.tex"
}
Rename-Item ".\content.tex" -NewName "content.old.tex" -Force
Get-ChildItem ".\content\" | ForEach-Object {
    $input = "\input{" + ($_.FullName | Resolve-Path -Relative).Replace('\','/') +"}"
    Write-Output "Writing $input to content.tex"
    $input | Out-File -Append -FilePath ./content.tex -Encoding ascii
}