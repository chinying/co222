edges, V = 0, 0
inf = 1 << 31

class Edge:
    def __init__(self, src, dest, weight):
        self.src = src
        self.dest = dest
        self.weight = weight

def readFile(filename):
    global edges, V
    f = open(filename)
    lines = f.read().split("\n")

    V = int(lines[0])
    lines = lines[1:-1]
    edges = []


    for line in lines:
        tokens = line.split(" ")
        start = int(tokens[0])
        n = len(tokens)
        i = 1
        while (i < n):
            end, wt = int(tokens[i]), int(tokens[i+1])
            edge = Edge(start, end, wt)
            edges.append(edge)
            i += 2

def bellmanford(src):
    global inf, edges, V
    dist, prev = [inf for i in range(V)], [None for i in range(V)]
    dist[src] = 0
    for i in range(0, V):
        for edge in edges:
            if dist[edge.src] + edge.weight < dist[edge.dest]:
                dist[edge.dest] = dist[edge.src] + edge.weight
                prev[edge.dest] = edge.src

    for edge in edges:
        if dist[edge.src] + edge.weight < dist[edge.dest]:
            raise Exception ("Graph contains a negative-weight cycle")
    return (dist, prev)

def main():

    infile = "input2.in"
    end = 5
    readFile(infile)

    sp = bellmanford(0)
    print sp[0]


if __name__ == "__main__":
    main()
