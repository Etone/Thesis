Write-Output "Updating content.tex"
if (Test-Path "./content.tex") {
    Remove-Item ".\content.tex"
}
Get-ChildItem ".\content\" | ForEach-Object {
    $input = "\input{" + ($_.FullName | Resolve-Path -Relative).Replace('\','/') +"}"
    Write-Output "Writing $input to content.tex"
    $input | Out-File -Append -FilePath ./content.tex -Encoding ascii
}