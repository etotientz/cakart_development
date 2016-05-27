import csv

with open('jan.csv',"r") as fin:
    with open('newjan.csv',"w") as fout:
        writer=csv.writer(fout)
        for row in csv.reader(fin):
            writer.writerow(row[:-2])
