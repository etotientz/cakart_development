lines = open('april.csv').read()
linesa=lines.split(',')
open('newfile.csv', 'w').writelines(linesa[0:4])
