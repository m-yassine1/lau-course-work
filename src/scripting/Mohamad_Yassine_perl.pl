#!/bin/perl
use List::Util qw[min max];

open(DATA,"dictionary.txt") or die "Couldn't open file!";
@list = <DATA>;
chomp(@list);
@list = sort{length $a <=> length $b} @list;

#creating an array of hashes words sorted by length
%alphabet={};
foreach $word (@list)
{
	$length = length($word);
	$alphabet{$word} = $length;
}
close DATA;

#print "$_ => $alphabet{$_}\n" for (keys %alphabet);

#Edit distance subroutine
sub edit_distance
{
	my @solution;
	my @string1 = split (//,lc(@_[0]));
	my @string2 = split (//,lc(@_[1]));
	
	#clear 2D array
	for($i = 0; $i <= @string2; $i++)
	{
		$solution[0][$i] = $i;
	}

        for($i = 0; $i <= @string1; $i++)
        {
                $solution[$i][0] = $i;
        }

	#Edit distance
	for($i = 1; $i <= @string1; $i++)
	{
                for($j = 1; $j <= @string2; $j++)
                {
                        if($string1[$i - 1] eq $string2[$j - 1])
			{
				$solution[$i][$j] = $solution[$i-1][$j-1];
			}
			else
			{
				$solution[$i][$j] = min($solution[$i-1][$j-1]+1,$solution[$i][$j-1]+1,$solution[$i-1][$j]+1);	
			}
                }
        }
	return $solution[@string1][@string2];
}

print $alphabet{"word"}."\n";

#print edit_distance("kitten","sitting")."\n";
$sentence = <STDIN>;
@words = split / /,$sentence;
#Maximum number of error
$TOLERANCE = 2;

WORDS:foreach $word (@words)
{
	@wordlist =();

	#if it exists jump to next word
	if(exists $alphabet{$word})
	{
		print "exists\n";
		next WORDS;
	}

	#Checking words of different length +-2
	@wording = grep {abs($alphabet{$_} - length($word)) <= 2} keys %alphabet;
	
	foreach $correct(@wording)
	{
		print $correct."\n";
	}
		
	foreach $correct(@wording)
	{
		if(edit_distance($word,$correct) <= 2)
		{
			push(@wordlist,$correct);
		}
	}

	print $word.": ". join(', ',@wordlist)."\n";
}
